package com.example.anteikucoffeeassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* This method is called when the user clicks on the button to decrease the number of coffee ordered */

    public void reduceQuantity(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 unit", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /* This method is called when the user clicks on the button to increase the number of coffee ordered */
    public void increaseQuantity(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "Max limit reached", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    /* This method is called when the user clicks on the order button, to finally place the order */
    public void placeOrder(View view) {

        EditText text = (EditText) findViewById(R.id.customer_name_text_view);
        String customerName = text.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        CheckBox cinnamonCheckbox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckbox.isChecked();

        CheckBox gingerMilkCheckbox = (CheckBox) findViewById(R.id.ginger_milk_checkbox);
        boolean hasGingerMilk = gingerMilkCheckbox.isChecked();

        CheckBox vanillaExtractCheckbox = (CheckBox) findViewById(R.id.vanilla_extract_checkbox);
        boolean hasVanillaExtract = vanillaExtractCheckbox.isChecked();

        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate, hasCinnamon, hasGingerMilk, hasVanillaExtract);

        String message = createOrderSummary(customerName, totalPrice, hasWhippedCream, hasChocolate, hasCinnamon, hasGingerMilk, hasVanillaExtract);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));                   //the "mailto" string value is for specifying that only the mailing apps should be able to handle this intent
    //    intent.putExtra(Intent.EXTRA_EMAIL, "anteikucoffeetokyo@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "This coffee order is for: " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /* To display the quantity of coffee ordered */
    private void displayQuantity(int numberOfCoffeeOrdered) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffeeOrdered);
    }

    /* Method to compute the total price of the order */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addGingerMilk, boolean addVanillaExtract) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice += 3;
        }
        if (addChocolate) {
            basePrice += 5;
        }
        if (addCinnamon) {
            basePrice += 2;
        }
        if (addGingerMilk) {
            basePrice += 7;
        }
        if (addVanillaExtract) {
            basePrice += 6;
        }

        return quantity * basePrice;
    }


    // method to create an order summary (in place of the price text view)

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addGingerMilk, boolean addVanillaExtract) {
        String priceMessage = "Customer Name: " + name;
        priceMessage += "\n" + "Whipped Cream added as a topping: " + addWhippedCream;
        priceMessage += "\n" + "Chocolate added as a topping: " + addChocolate;
        priceMessage += "\n" + "Cinnamon added as a topping: " + addCinnamon;
        priceMessage += "\n" + "Ginger Milk added as a topping: " + addGingerMilk;
        priceMessage += "\n" + "Vanilla Extract added as a topping: " + addVanillaExtract;
        priceMessage += "\n" + "Quantity Ordered: " + quantity;
        priceMessage += "\n" + "Order Amount: " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

}