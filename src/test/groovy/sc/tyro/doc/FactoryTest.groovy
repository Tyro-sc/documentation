package sc.tyro.doc

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sc.tyro.bundle.html5.Div
import sc.tyro.core.component.Button
import sc.tyro.core.component.Panel

import static sc.tyro.core.Tyro.*
import static sc.tyro.doc.TyroExtension.BASE_URL

@ExtendWith(TyroExtension)
@DisplayName("Factory Tests")
class FactoryTest {
    @BeforeAll
    static void before() {
        visit BASE_URL + '/components.html'
    }

    @Test
    @DisplayName("Should find button by text")
    void findButton() {
        Button button = button('Submit')
        button.should { have text('Submit') }
    }

    @Test
    @DisplayName("Should find button by text in the context of a parent")
    void findButtonInParent() {
        // tag::factory[]
        Panel panel = $('#parent') as Div
        Button button = button('Ok', on(panel)) // => Find button with text Ok only in the panel
        button.should { be visible }
        // end::factory[]
    }
}
