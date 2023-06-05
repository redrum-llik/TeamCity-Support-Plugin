package jetbrains.buildServer.supportAssist.execution.impl

import jetbrains.buildServer.supportAssist.execution.ExecutionScenario
import jetbrains.buildServer.supportAssist.execution.ScenarioStep

abstract class AbstractExecutionScenario(
    steps: List<ScenarioStep>
) : ExecutionScenario {
    private val mySteps: List<ScenarioStep> = steps

    override val size: Int
        get() = mySteps.size

    override fun contains(element: ScenarioStep): Boolean {
        return mySteps.contains(element)
    }

    override fun containsAll(elements: Collection<ScenarioStep>): Boolean {
        return mySteps.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return mySteps.isEmpty()
    }

    override fun iterator(): Iterator<ScenarioStep> {
        return mySteps.iterator()
    }
}