package sc.tyro.doc

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import sc.tyro.bundle.html5.Div
import sc.tyro.bundle.html5.input.InputTypeRange
import sc.tyro.core.component.CheckBox
import sc.tyro.core.component.Panel
import sc.tyro.core.component.field.EmailField
import sc.tyro.core.component.field.Field
import sc.tyro.core.component.field.RangeField

import static sc.tyro.core.Tyro.*
import static sc.tyro.core.input.Key.SHIFT
import static sc.tyro.doc.TyroExtension.BASE_URL

@ExtendWith(TyroExtension)
@DisplayName("Interactions Tests")
class InteractionTest {
    @Test
    @DisplayName("Mouse")
    void mouse() {
        visit BASE_URL + '/interaction.html'

        Field component = field('Email', EmailField)
        Panel panel = $('#drag') as Div
        Panel dropZone = $('#drop-zone') as Div

        // tag::mouse[]
        clickOn component
        doubleClickOn component
        rightClickOn component
        drag panel on dropZone
        // end::mouse[]
    }

    @Test
    @DisplayName("Keyboard")
    void keyboard() {
        visit BASE_URL + '/interaction.html'

        // tag::keyboard[]
        Field emailField = field('Email', EmailField)

        emailField.should { have value("") }
        clickOn emailField // To get the focus
        type SHIFT + 'tyro'
        emailField.should { have value('TYRO') }
        // end::keyboard[]
    }

    @Test
    @DisplayName("Intention")
    void intention() {
        visit BASE_URL + '/interaction.html'

        // tag::check[]
        CheckBox checkBox = checkbox('Checkbox Label')
        checkBox.should { be unchecked }

        check checkBox

        checkBox.should { be checked }
        // end::check[]

        // tag::uncheck[]
        checkBox.should { be checked }

        uncheck checkBox

        checkBox.should { be unchecked }
        // end::uncheck[]

        // tag::fill[]
        EmailField emailField = field('Email')

        emailField.should { be empty }

        fill emailField with 'joe.black@email.org'

        emailField.should {
            be filled
            have value('joe.black@email.org')
        }
        // end::fill[]

        // tag::clear[]
        emailField.should {
            be filled
            have value('joe.black@email.org')
        }

        clear emailField

        emailField.should { be empty }
        // end::clear[]


        // tag::set[]
        RangeField range = field('Range', InputTypeRange)

        range.should { have value(10) }

        set range to 20

        range.should { have value(20) }
        // end::set[]


    }
}
