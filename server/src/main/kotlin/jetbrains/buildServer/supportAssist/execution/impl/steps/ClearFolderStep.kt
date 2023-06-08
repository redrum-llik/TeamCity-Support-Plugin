package jetbrains.buildServer.supportAssist.execution.impl.steps

import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.errors.ClearFolderError
import jetbrains.buildServer.supportAssist.execution.impl.errors.NotAFolderError
import java.io.File
import java.nio.file.Path

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
                val error = ClearFolderError(folder)
                addError(error)
            }
        } else {
            val error = NotAFolderError(folder)
            addError(error)
        }
    }
}