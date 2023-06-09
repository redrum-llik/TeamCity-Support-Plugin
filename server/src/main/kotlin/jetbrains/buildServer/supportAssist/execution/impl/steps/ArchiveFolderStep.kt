package jetbrains.buildServer.supportAssist.execution.impl.steps

import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.errors.ArchiveFailedProblem
import jetbrains.buildServer.supportAssist.execution.impl.errors.NotAFolderProblem
import jetbrains.buildServer.supportAssist.execution.impl.errors.WriteToFileProblem
import jetbrains.buildServer.util.ArchiveUtil
import java.io.File
import java.io.FileNotFoundException

/**
 * This class represents a step in a scenario that archives given folder to a file.
 * @param archiveFile target archive file.
 * @param folder the folder to be archived.
 */
class ArchiveFolderStep(
    private val archiveFile: File,
    private val folder: File
) : AbstractScenarioStep() { // #TODO: consider allowing to specify other archive formats here
    override fun doExecute() {
        if (folder.isDirectory) {
            try {
                val result = ArchiveUtil.packZip(
                    archiveFile,
                    listOf(folder)
                )
                if (!result) {
                    val problem = ArchiveFailedProblem(archiveFile, folder)
                    addProblem(problem)
                }
            } catch (e: FileNotFoundException) {
                val problem = WriteToFileProblem(e)
                addProblem(problem)
            }
        } else {
            val problem = NotAFolderProblem(folder)
            addProblem(problem)
        }
    }

    override fun describe(): String {
        return "Archive folder at '${folder.absolutePath}' path to '${archiveFile.absolutePath}' file"
    }
}