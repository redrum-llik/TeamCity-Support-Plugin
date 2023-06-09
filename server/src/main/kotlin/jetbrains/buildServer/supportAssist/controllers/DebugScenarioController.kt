package jetbrains.buildServer.supportAssist.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.supportAssist.execution.ExecutionScenario
import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import jetbrains.buildServer.supportAssist.execution.impl.AbstractExecutionScenario
import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.ProblemImpl
import jetbrains.buildServer.supportAssist.execution.impl.steps.ArchiveFolderStep
import jetbrains.buildServer.supportAssist.execution.impl.steps.FetchServerInfoToFileStep
import jetbrains.buildServer.util.ExceptionUtil
import jetbrains.buildServer.util.FileUtil
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.jetbrains.annotations.NotNull
import org.springframework.web.servlet.ModelAndView
import java.io.File
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DebugScenarioController(
    @NotNull server: SBuildServer,
    @NotNull webControllerManager: WebControllerManager
) : BaseController(server) {
    init {
        webControllerManager.registerController("/support/debug.html", this)
    }

    private fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ModelAndView {
        return simpleView("Hello! I am a controller that can run a debug support assist scenario if you send a POST request to me.")
    }

    private fun doPost(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ModelAndView {
        val tempFolder = FileUtil.createTempDirectory(
            "supportPluginData",
            null
        )

        val serverInfoFile = File(tempFolder, "serverInfo.json")

        val fetchServerInfoStep = FetchServerInfoToFileStep(
            serverInfoFile,
            myServer
        )

        val archiveFile = File(tempFolder, "out.zip")

        val archiveFolderStep = ArchiveFolderStep(
            archiveFile,
            tempFolder
        )

        val steps = listOf(fetchServerInfoStep, archiveFolderStep)

        // define a minimal scenario with above step
        val scenario = object : AbstractExecutionScenario(steps) {
            override fun execute(): List<StepExecutionProblem> {
                for (step in this) {
                    step.execute()
                    if (step.hasErrorLevelProblems()) {
                        break // do not proceed if step reports at least one error-level execution problem
                    }

                }
                return listOf() // for now it returns empty list; #TODO: see if ExecutionScenario interface should be changed
            }
        }

        // execute scenario and return JSON report
        scenario.execute()
        return simpleView(
            generateReport(scenario)
        )
    }

    override fun doHandle(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ModelAndView {
        return when (request.method) {
            "POST" -> {
                doPost(request, response)
            }

            "GET" -> {
                doGet(request, response)
            }

            else -> simpleView("Only GET and POST methods are supported for this endpoint")
        }
    }

    companion object {
        fun generateReport(scenario: ExecutionScenario): String {
            val mapper = jacksonObjectMapper()
                .writerWithDefaultPrettyPrinter()

            return mapper.writeValueAsString(scenario)
        }
    }
}
