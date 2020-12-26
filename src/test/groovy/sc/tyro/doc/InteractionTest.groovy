package sc.tyro.doc

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sc.tyro.bundle.html5.Div
import sc.tyro.core.component.Panel
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.Field
import sc.tyro.core.input.Mouse

import static sc.tyro.core.Tyro.*
import static sc.tyro.doc.TyroExtension.BASE_URL


@DisplayName("Interactions Tests")
class InteractionTest {
    @Nested
    @ExtendWith(TyroExtension)
    @DisplayName("Imperative way")
    class Imperative {
        @Test
        @DisplayName("Mouse")
        void mouse() {
            visit BASE_URL + '/interaction.html'

            Field field = field('Email', EmailField)
            Panel panel = $('#drag') as Div
            Panel dropZone = $('#drop-zone') as Div

            // tag::mouse[]
            field.click()
            field.doubleClick()
            field.rightClick()
            panel.drag().on(dropZone)
            // end::mouse[]
        }
    }


    @Nested
    @ExtendWith(TyroExtension)
    @DisplayName("Functional way")
    class Functional {

    }

}
