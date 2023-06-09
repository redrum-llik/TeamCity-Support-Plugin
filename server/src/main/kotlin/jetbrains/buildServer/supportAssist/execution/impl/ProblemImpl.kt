package jetbrains.buildServer.supportAssist.execution.impl

import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import jetbrains.buildServer.supportAssist.execution.Severity

/**
 * Implementation of the StepExecutionError interface.
 * Provides a description and severity level for errors that occur during a step execution.
 * @param myDescription A string containing the description of the error.
 * @param mySeverity An optional [Severity] enum value indicating the severity level of the error.
 */
class ProblemImpl(
    private val myDescription: String,
    private val mySeverity: Severity = Severity.ERROR
) : StepExecutionProblem {
    override fun getDescription(): String {
        return myDescription
    }

    override fun getSeverity(): Severity {
        return mySeverity
    }

    override fun toString(): String {
        return this.javaClass.simpleName +
                "[description='${myDescription}', severity='${mySeverity}']"
    }
}