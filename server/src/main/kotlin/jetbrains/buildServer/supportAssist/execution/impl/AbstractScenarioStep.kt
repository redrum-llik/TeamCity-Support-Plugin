package jetbrains.buildServer.supportAssist.execution.impl

import jetbrains.buildServer.supportAssist.execution.ScenarioStep
import jetbrains.buildServer.supportAssist.execution.StepExecutionError

abstract class AbstractScenarioStep : ScenarioStep {
    private var myIsExecuted = false
    private val myErrors: ArrayList<StepExecutionError> = arrayListOf()

    override fun isExecuted(): Boolean {
        return myIsExecuted
    }

    abstract fun doExecute()

    override fun execute() {
        doExecute()
        myIsExecuted = true
    }

    override fun getErrors(): List<StepExecutionError> {
        return myErrors
    }

    protected fun addError(error: StepExecutionError) {
        myErrors.add(error)
    }
}