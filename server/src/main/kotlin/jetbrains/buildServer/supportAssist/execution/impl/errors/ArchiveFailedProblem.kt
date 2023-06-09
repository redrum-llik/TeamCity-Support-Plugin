package jetbrains.buildServer.supportAssist.execution.impl.errors

import jetbrains.buildServer.supportAssist.execution.Severity
import jetbrains.buildServer.supportAssist.execution.StepExecutionProblem
import java.io.File

class ArchiveFailedProblem(
    private val archiveFile: File,
    private val folder: File
) : StepExecutionProblem {
    override fun getDescription(): String {
        return "Could not archive folder '${folder.absolutePath}' to '${archiveFile.absolutePath}'"
    }

    override fun getSeverity(): Severity {
        return Severity.ERROR
    }
}