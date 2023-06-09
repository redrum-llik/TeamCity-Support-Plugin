package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem

/**
 * Represents an error when there is an exception thrown while writing to a file.
 *
 * @property throwable The throwable object that was caught.
 */
class WriteToFileProblem(
    private val throwable: Throwable
) : StepExecutionProblem {
    override fun getDescription(): String {
        return "Caught an exception while writing to file: '${throwable.message}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}