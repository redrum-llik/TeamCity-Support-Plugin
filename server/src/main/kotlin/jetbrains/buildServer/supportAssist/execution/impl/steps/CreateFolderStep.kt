package jetbrains.buildServer.supportAssist.execution.impl.steps

import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.errors.FolderAlreadyExists
import jetbrains.buildServer.supportAssist.execution.impl.errors.NotAFolderProblem
import java.io.File

/**
 * Represents a step in a scenario that creates a folder.
 * @param folder the folder to be created
 */
class CreateFolderStep(
    private val folder: File
) : AbstractScenarioStep() {
    override fun doExecute() {
        if (folder.isDirectory) {
            val result = folder.mkdir()
            if (!result) {
                val problem = FolderAlreadyExists(folder)
                addProblem(problem)
            }
        } else {
            val problem = NotAFolderProblem(folder)
            addProblem(problem)
        }
    }

    override fun describe(): String {
        return "Create folder at '${folder.absolutePath}' path"
    }
}