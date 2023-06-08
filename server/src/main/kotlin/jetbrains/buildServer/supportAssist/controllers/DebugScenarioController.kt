package jetbrains.buildServer.supportAssist.controllers

import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionError
import jetbrains.buildServer.supportAssist.execution.impl.AbstractExecutionScenario
import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.ErrorImpl
import jetbrains.buildServer.supportAssist.execution.impl.steps.FetchServerInfoToFolderStep
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.jetbrains.annotations.NotNull
import org.springframework.web.servlet.ModelAndView
import java.io.File
import java.nio.file.Path
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
        // define a minimal step that just prints something to log
        // and creates a mock error to be returned by controller
        val minimalStep = object : AbstractScenarioStep() {
            override fun doExecute() {
                Loggers.SERVER.warn("This scenario works!")
                val error = ErrorImpl(
                    "This is an example of thrown error",
                    Severity.WARNING
                )
                addError(error)
            }
        }

        val steps = listOf(minimalStep)

        // define a minimal scenario with above step
        val scenario = object : AbstractExecutionScenario(steps) {
            override fun execute(): List<StepExecutionError> {
                val errors: MutableList<List<StepExecutionError>> = mutableListOf()
                for (step in this) {
                    step.execute()
                    errors.add(step.getErrors())
                }
                return errors.flatten()
            }
        }

        // execute scenario and return list of errors serialized to multiline string
        return simpleView(
            scenario
                .execute()
                .joinToString(
                    System.lineSeparator()
                )
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
}
