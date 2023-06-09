package jetbrains.buildServer.supportAssist.execution.impl

import jetbrains.buildServer.supportAssist.execution.ScenarioStep
import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem

/**
 * An abstract class that implements the [ScenarioStep] interface and provides basic functionality
 * for executing scenario steps and handling execution errors.
 */
abstract class AbstractScenarioStep : ScenarioStep {
    private var myIsExecuted = false
    private val myProblems: ArrayList<StepExecutionProblem> = arrayListOf()

    override fun isExecuted(): Boolean {
        return myIsExecuted
    }

    abstract fun doExecute()

    override fun execute() {
        doExecute()
        myIsExecuted = true
    }

    override fun getProblems(): List<StepExecutionProblem> {
        return myProblems
    }

    override fun hasErrorLevelProblems(): Boolean {
        return myProblems.any { it.getSeverity() == Severity.ERROR }
    }

    protected fun addProblem(error: StepExecutionProblem) {
        myProblems.add(error)
    }
}