package edu.itstep.hw20221119;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.itstep.hw20221119.models.ConstantsStore;
import edu.itstep.hw20221119.models.Order;
import edu.itstep.hw20221119.models.PizzaRecipe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // поля - змінні класу, що відповідають активним елементам Activity
    private TextView tvCountPizza;

    private ImageButton imageButtonMinus;
    private ImageButton imageButtonPlus;

    private ImageButton ibPizza_1;
    private ImageButton ibPizza_2;
    private ImageButton ibPizza_3;

    private ConstraintLayout clPizza_1;
    private ConstraintLayout clPizza_2;
    private ConstraintLayout clPizza_3;

    private Button btnShowActivitySize;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(); // ініціалізація даних
        setListener(); // підключення слухачів
        initData(); // ініціалізація первинних даних
    }

    private void initView() {
        tvCountPizza = findViewById(R.id.tvCountMeat);
        imageButtonMinus = findViewById(R.id.ibMinusMeat);
        imageButtonPlus = findViewById(R.id.ibPlusMeat);

        ibPizza_1 = findViewById(R.id.ibPizza_1);
        ibPizza_2 = findViewById(R.id.ibPizza_2);
        ibPizza_3 = findViewById(R.id.ibPizza_3);

        clPizza_1 = findViewById(R.id.clPizza_1);
        clPizza_2 = findViewById(R.id.clPizza_2);
        clPizza_3 = findViewById(R.id.clPizza_3);

        btnShowActivitySize = findViewById(R.id.btnShowActivitySize);
    }

    private void setListener() {
        imageButtonPlus.setOnClickListener(this);
        imageButtonMinus.setOnClickListener(this);

        ibPizza_1.setOnClickListener(this);
        ibPizza_2.setOnClickListener(this);
        ibPizza_3.setOnClickListener(this);
        clPizza_1.setOnClickListener(this);
        clPizza_2.setOnClickListener(this);
        clPizza_3.setOnClickListener(this);

        btnShowActivitySize.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int btn_id = view.getId();
        switch (btn_id) {
            case R.id.ibPlusMeat: {
                changeCountPizza(true);
                break;
            }
            case R.id.ibMinusMeat: {
                changeCountPizza(false);
                break;
            }
            case R.id.ibPizza_1:
            case R.id.ibPizza_2:
            case R.id.ibPizza_3:
            case R.id.clPizza_1:
            case R.id.clPizza_2:
            case R.id.clPizza_3: {
                choosePizzaRecipe(btn_id);
                break;
            }
            case R.id.btnShowActivitySize: {
                showPizzaSizeActivity();
                break;
            }
        }
    }

    private void showPizzaSizeActivity() {
        if (tvCountPizza.getText().equals(tvCountPizzaStart())) {
            showChoosePizzaRecipeMassage();
        } else {
            Intent intent = new Intent(this, PizzasizeActivity.class);
            intent.putExtra(ConstantsStore.KEY_ORDER, order);
            startActivity(intent);
        }
    }

    private void choosePizzaRecipe(int btn_id) {
        // отримання та встановлення кольору
//        int notChooseColor = resources.getColor(R.color.cl_notChoose);
//        int ChooseColor = resources.getColor(R.color.cl_Choose);
//        clPizza_1.setBackgroundColor(ChooseColor);

        Resources resources = getResources();
        Drawable drawableClChooseRecipe = resources.getDrawable(R.drawable.rectangle_rounded_choose_recipe);
        Drawable drawableClNotChooseRecipe = resources.getDrawable(R.drawable.rectangle_rounded);
        Drawable drawableIbChooseRecipe = resources.getDrawable(R.drawable.circle_ib_pizza_choose);
        Drawable drawableIbNotChooseRecipe = resources.getDrawable(R.drawable.circle_ib_pizza_notchoose);

        switch (btn_id) {
            case R.id.ibPizza_1:
            case R.id.clPizza_1: {
                clPizza_1.setBackground(drawableClChooseRecipe);
                clPizza_2.setBackground(drawableClNotChooseRecipe);
                clPizza_3.setBackground(drawableClNotChooseRecipe);
                ibPizza_1.setBackground(drawableIbChooseRecipe);
                ibPizza_2.setBackground(drawableIbNotChooseRecipe);
                ibPizza_3.setBackground(drawableIbNotChooseRecipe);
                order.setPizzaRecipe(PizzaRecipe.PANCHETA_GORGONDZOLA);
                break;
            }
            case R.id.ibPizza_2:
            case R.id.clPizza_2: {
                clPizza_1.setBackground(drawableClNotChooseRecipe);
                clPizza_2.setBackground(drawableClChooseRecipe);
                clPizza_3.setBackground(drawableClNotChooseRecipe);
                ibPizza_1.setBackground(drawableIbNotChooseRecipe);
                ibPizza_2.setBackground(drawableIbChooseRecipe);
                ibPizza_3.setBackground(drawableIbNotChooseRecipe);
                order.setPizzaRecipe(PizzaRecipe.MEAT_PIZZA);
                break;
            }
            case R.id.ibPizza_3:
            case R.id.clPizza_3: {
                clPizza_1.setBackground(drawableClNotChooseRecipe);
                clPizza_2.setBackground(drawableClNotChooseRecipe);
                clPizza_3.setBackground(drawableClChooseRecipe);
                ibPizza_1.setBackground(drawableIbNotChooseRecipe);
                ibPizza_2.setBackground(drawableIbNotChooseRecipe);
                ibPizza_3.setBackground(drawableIbChooseRecipe);
                order.setPizzaRecipe(PizzaRecipe.PHILADELPHIA);
                break;
            }
        }
        if (tvCountPizza.getText().equals(tvCountPizzaStart())) {
            order.setPizzaCount(1);
            tvCountPizza.setText("1");
        }
    }

    private void changeCountPizza(boolean isPlus) {
        if (!tvCountPizza.getText().toString().equals(tvCountPizzaStart())) {
            int countPizza = Integer.parseInt(tvCountPizza.getText().toString());
            countPizza = isPlus ? countPizza + 1 : countPizza > 1 ? countPizza - 1 : countPizza;
            order.setPizzaCount(countPizza);
            tvCountPizza.setText("" + countPizza);
        } else {
            showChoosePizzaRecipeMassage();
        }
    }

    private String tvCountPizzaStart() {
        Resources resources = getResources();
        return resources.getString(R.string.tvCountPizza);
    }

    private void showChoosePizzaRecipeMassage() {
        Resources resources = getResources();
        String message = resources.getString(R.string.messageChooseRecipe);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        order = new Order();
    }

}