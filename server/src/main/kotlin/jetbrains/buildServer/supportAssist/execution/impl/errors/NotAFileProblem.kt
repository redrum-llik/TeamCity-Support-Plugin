package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import java.io.File

/**
 * An error class to be used for cases when a file is expected but a folder is received.
 *
 * @param file the unexpected object that caused the error.
 */
class NotAFileProblem(
    private val file: File
) : StepExecutionProblem {
    override fun getDescription(): String {
        return "Expected file but got a folder: '${file.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}