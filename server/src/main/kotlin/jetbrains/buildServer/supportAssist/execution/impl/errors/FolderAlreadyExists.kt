package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import java.io.File

/**
 * Represents an error that occurred due to an attempt to create a folder that already exists.
 *
 * @param folder The [File] that already exists and causes the error to be generated.
 */
class FolderAlreadyExists(
    private val folder: File
) : StepExecutionProblem {
    override fun getDescription(): String {
        return "Folder already exists at path: '${folder.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.WARNING
    }
}