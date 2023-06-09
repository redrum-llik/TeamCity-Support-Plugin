package jetbrains.buildServer.supportAssist.controllers

import jetbrains.buildServer.controllers.BaseController
import jetbrains.buildServer.log.Loggers
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import jetbrains.buildServer.supportAssist.execution.impl.AbstractExecutionScenario
import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.ProblemImpl
import jetbrains.buildServer.web.openapi.WebControllerManager
import org.jetbrains.annotations.NotNull
import org.springframework.web.servlet.ModelAndView
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
                val error = ProblemImpl(
                    "This is an example of thrown error",
                    Severity.WARNING
                )
                addProblem(error)
            }

            override fun describe(): String {
                return "This is a mock step that just logs something into server log and returns a warning problem"
            }
        }

        val steps = listOf(minimalStep)

        // define a minimal scenario with above step
        val scenario = object : AbstractExecutionScenario(steps) {
            override fun execute(): List<StepExecutionProblem> {
                val problems: MutableList<List<StepExecutionProblem>> = mutableListOf()
                for (step in this) {
                    step.execute()
                    problems.add(step.getProblems())
                    if (step.hasErrorLevelProblems()) {
                        break // do not proceed if step reports at least one error-level execution problem
                    }

                }
                return problems.flatten()
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
