/**
 * Клас, що представляє їжу в автоматі. Наслідує Product і реалізує шаблон Builder.
 */
public class Food extends Product {
    private final int weight; // Вага в грамах

    /**
     * Внутрішній статичний клас Builder для створення об'єктів Food.
     */
    public static class Builder extends Product.Builder<Builder> {
        private int weight = 100; // Вага за замовчуванням

        /**
         * Конструктор з обов'язковими параметрами.
         * @param name Назва продукту.
         * @param price Ціна продукту.
         */
        public Builder(String name, double price) {
            super(name, price);
        }

        /**
         * Встановлює вагу продукту.
         * @param val Вага в грамах.
         * @return Поточний екземпляр Builder.
         */
        public Builder weight(int val) {
            this.weight = val;
            return this;
        }

        @Override
        public Food build() {
            return new Food(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private Food(Builder builder) {
        super(builder);
        this.weight = builder.weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getDescription() {
        return String.format("Їжа: %s, Вага: %dг, Ціна: %.2f грн", getName(), getWeight(), getPrice());
    }
}
