/**
 * Фабричний метод (Factory Method) для створення різних типів продуктів.
 * Використовує шаблон Builder для конструювання об'єктів продуктів.
 * 
 * GRASP Patterns:
 * - Creator: Цей клас відповідає за створення об'єктів Product, оскільки він
 *   має необхідну інформацію про їхні типи та процес створення.
 */
public class ProductFactory {

    /**
     * Створює напій з використанням Builder.
     * @param name Назва напою.
     * @param price Ціна.
     * @param volume Об'єм в літрах.
     * @return Створений об'єкт Drink.
     */
    public static Drink createDrink(String name, double price, double volume) {
        return new Drink.Builder(name, price)
                .volume(volume)
                .build();
    }

    /**
     * Створює їжу з використанням Builder.
     * @param name Назва.
     * @param price Ціна.
     * @param weight Вага в грамах.
     * @return Створений об'єкт Food.
     */
    public static Food createFood(String name, double price, int weight) {
        return new Food.Builder(name, price)
                .weight(weight)
                .build();
    }
    
    /**
     * Створює їжу з додатковими параметрами.
     * @param name Назва.
     * @param price Ціна.
     * @param weight Вага.
     * @param calories Калорійність.
     * @return Створений об'єкт Food.
     */
    public static Food createFoodWithExtras(String name, double price, int weight, int calories, String expDate) {
        return new Food.Builder(name, price)
                .weight(weight)
                .calories(calories)
                .expirationDate(expDate)
                .build();
    }
}
