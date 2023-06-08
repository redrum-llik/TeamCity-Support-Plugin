package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionError
import java.io.File

/**
 * An error class to be used for cases when a file is expected but a folder is received.
 *
 * @param file the unexpected object that caused the error.
 */
class NotAFileError(
    private val file: File
) : StepExecutionError {
    override fun getDescription(): String {
        return "Expected file but got a folder: '${file.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}