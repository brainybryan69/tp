package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.model.followup.FollowUp;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox transactions;
    @FXML
    private VBox followUps;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress() != null ? person.getAddress().value : "No address provided");
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        // Display transactions
        if (!person.getTransactions().isEmpty()) {
            Label transactionHeader = new Label("Transactions:");
            transactionHeader.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 2 0;");
            transactions.getChildren().add(transactionHeader);

            for (int i = 0; i < person.getTransactions().size(); i++) {
                Transaction txn = person.getTransactions().get(i);
                Label transactionLabel = new Label(String.format("%d. %s", i + 1, txn.toString()));
                transactionLabel.setStyle("-fx-font-size: 11px; -fx-padding: 1 0 1 10;");
                transactions.getChildren().add(transactionLabel);
            }
        }

        // Display FollowUps
        if (!person.getFollowUps().isEmpty()) {
            Label followUpHeader = new Label("FollowUps:");
            followUpHeader.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 2 0;");
            followUps.getChildren().add(followUpHeader);

            for (int i = 0; i < person.getFollowUps().size(); i++) {
                FollowUp fu = person.getFollowUps().get(i);
                // Create a circle and label in an HBox (for horizontal alignment)
                HBox hbox = new HBox(10); // 10 is the spacing between elements

                // Create and style the circle
                Circle circle = new Circle(5); // 5 is the radius
                circle.setStyle(" -fx-padding: 0 0 0 0;");


                switch (fu.getUrgency()) {
                case HIGH:
                    circle.setFill(Color.RED);
                    break;
                case MEDIUM:
                    circle.setFill(Color.ORANGE);
                    break;
                case LOW:
                    circle.setFill(Color.YELLOW);
                    break;
                default:
                    break;
                }

                // Create the label
                Label followUpLabel = new Label(String.format("%d. %s", i + 1, fu.toString()));
                followUpLabel.setStyle("-fx-font-size: 11px; -fx-padding: 1 0 1 10;");

                // Add circle and label to HBox
                hbox.getChildren().addAll(followUpLabel, circle);
                hbox.setAlignment(Pos.CENTER_LEFT);
                followUps.getChildren().add(hbox);
            }
        }
    }
}
