package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import java.io.File

/**
 * Represents an error that occurred while attempting to clear a folder.
 * @property folder The [File] representing the folder that could not be cleared.
 */
class ClearFolderProblem(
    private val folder: File
): StepExecutionProblem {
    override fun getDescription(): String {
        return "Could not clear folder at path: '${folder.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.WARNING
    }
}