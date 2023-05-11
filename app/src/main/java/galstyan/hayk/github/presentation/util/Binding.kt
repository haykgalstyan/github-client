package galstyan.hayk.github.presentation.util

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        binding ?: createBinding(thisRef)

    private fun createBinding(fragment: Fragment): T {
        check(fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            "Tried to access bindings when Fragment's views are destroyed."
        }

        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(
            object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                    if (f === fragment) {
                        binding = null
                        fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(this)
                    }
                }
            },
            false
        )

        return viewBindingFactory(fragment.requireView()).also { binding = it }
    }
}

fun <T : ViewBinding> viewBinding(viewBindingFactory: (View) -> T): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }