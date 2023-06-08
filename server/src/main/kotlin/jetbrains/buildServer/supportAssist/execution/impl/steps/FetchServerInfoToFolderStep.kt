package jetbrains.buildServer.supportAssist.execution.impl.steps

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.supportAssist.execution.impl.AbstractScenarioStep
import jetbrains.buildServer.supportAssist.execution.impl.errors.NotAFileError
import jetbrains.buildServer.supportAssist.execution.impl.errors.WriteToFileError
import jetbrains.buildServer.util.DiagnosticUtil
import java.io.File

/**
 * A step implementation that fetches TeamCity server information and writes it to a specified file in JSON format.
 *
 * @property targetFile the file to write server information to
 * @property buildServer the [SBuildServer] instance to fetch information from
 */
class FetchServerInfoToFolderStep(
    private val targetFile: File,
    private val buildServer: SBuildServer
) : AbstractScenarioStep() {
    override fun doExecute() {
        val info: HashMap<String, Any> = hashMapOf()
        info["serverVersion"] = buildServer.fullServerVersion
        info["javaDetails"] = DiagnosticUtil.getJavaDetails()
        info["licensingPolicy"] = buildServer.licensingPolicy

        val mapper = jacksonObjectMapper()

        if (targetFile.isDirectory) {
            val error = NotAFileError(targetFile)
            addError(error)
            return
        } else {
            try {
                targetFile.createNewFile()
                mapper.writeValue(targetFile, info)
            } catch (e: Exception) {
                val error = WriteToFileError(e)
                addError(error)
            }
        }
    }
}