package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionError
import java.io.File

/**
 * Represents an error that occurs when the given file is not a folder.
 *
 * @property folder The [File] that was expected to be a folder.
 */
class NotAFolderError(
    private val folder: File
) : StepExecutionError {
    override fun getDescription(): String {
        return "Expected folder but got a file: '${folder.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}