package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import java.io.File

/**
 * Represents an error that occurs when the given file is not a folder.
 *
 * @property folder The [File] that was expected to be a folder.
 */
class NotAFolderProblem(
    private val folder: File
) : StepExecutionProblem {
    override fun getDescription(): String {
        return "Expected folder but got a file: '${folder.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}