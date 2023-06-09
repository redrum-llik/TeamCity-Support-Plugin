package jetbrains.buildServer.supportAssist.execution.impl.steps

import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.errors.ClearFolderProblem
import jetbrains.buildServer.supportAssist.execution.impl.errors.NotAFolderProblem
import java.io.File

/**
 * This class represents a step in a scenario that clears the contents of a given folder.
 * @param folder the folder to be cleared.
 */
class ClearFolderStep(
    private val folder: File
): AbstractScenarioStep() {
    override fun doExecute() {
        if (folder.isDirectory) {
            val result = folder.deleteRecursively()
            if (!result) {
                val problem = ClearFolderProblem(folder)
                addProblem(problem)
            }
        } else {
            val problem = NotAFolderProblem(folder)
            addProblem(problem)
        }
    }

    override fun describe(): String {
        return "Clear folder at '${folder.absolutePath}' path"
    }
}