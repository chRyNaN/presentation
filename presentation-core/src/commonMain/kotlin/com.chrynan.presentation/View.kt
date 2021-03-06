@file:Suppress("unused")

package com.chrynan.presentation

import kotlinx.coroutines.flow.Flow

/**
 * Represents a piece of UI, such as, a Screen. A [View] emits [Intent]s of type [I] that are accessible to callers of
 * the [intents] function. A [View] displays the UI for a provided [State] when the [render] function is called.
 * Other than the [render] function being called, a [View] should avoid updating it's UI state internally as it desires
 * (excluding non-UI state changes such as immediate and temporary reactions to [Intent]s, such as, the background
 * color briefly changing when the User clicks on a item in a list).
 *
 * A [View] typically contains a [Presenter] which is responsible for listening to the [View]s [intents] and performing
 * all the logic to derive a new [State] which it then passes back to the [View] via the [render] function.
 */
interface View<I : Intent, S : State> {

    /**
     * The currently rendered [State]. This property could briefly differ from the
     * [Presenter.currentState] until that state is provided to the [render] function and is
     * rendered to display.
     */
    val renderState: S?

    /**
     * A [Flow] of all of the [State]s. This [Flow] should emit all of the distinct states provided
     * to the [render] function.
     *
     * Note that the [Presenter] should not subscribe to these [states] as that would create a loop.
     */
    val states: Flow<S>

    /**
     * Provides all the [Intent]s of type [I] as a [Flow] to the caller. These can be caused by user input events
     * (clicks, scrolls, etc) or by other means.
     *
     * Note that this function should be called from the [Presenter].
     */
    fun intents(): Flow<I>

    /**
     * Renders the provided [state] of type [S] into a UI. The [state] should have everything necessary for the [View]
     * to display its UI.
     *
     * Note that this function should be called from the [Presenter].
     */
    fun render(state: S)

    companion object
}
