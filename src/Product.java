/**
 * Абстрактний базовий клас для всіх продуктів у торговому автоматі.
 * Використовує шаблон Builder для створення об'єктів, що дозволяє гнучко створювати продукти
 * з обов'язковими та необов'язковими параметрами.
 */
public abstract class Product {
    private final String name;
    private final double price;
    private final int calories; // Необов'язковий параметр
    private final String expirationDate; // Необов'язковий параметр

    /**
     * Абстрактний внутрішній клас Builder для ланцюжкового створення об'єктів Product.
     * @param <T> Тип конкретного будівельника, що наслідує цей клас (для підтримки ланцюжкових викликів).
     */
    public static abstract class Builder<T extends Builder<T>> {
        // Обов'язкові параметри
        private final String name;
        private final double price;

        // Необов'язкові параметри зі значеннями за замовчуванням
        private int calories = 0;
        private String expirationDate = "N/A";

        /**
         * Конструктор з обов'язковими параметрами.
         * @param name Назва продукту.
         * @param price Ціна продукту.
         */
        public Builder(String name, double price) {
            this.name = name;
            this.price = price;
        }

        /**
         * Встановлює калорійність продукту.
         * @param val Кількість калорій.
         * @return Поточний екземпляр Builder.
         */
        public T calories(int val) {
            calories = val;
            return self();
        }

        /**
         * Встановлює термін придатності.
         * @param date Дата у форматі String.
         * @return Поточний екземпляр Builder.
         */
        public T expirationDate(String date) {
            expirationDate = date;
            return self();
        }

        /**
         * Абстрактний метод для створення кінцевого об'єкта Product.
         * @return Створений продукт.
         */
        abstract Product build();

        /**
         * Абстрактний метод, що повертає `this` для підтримки ланцюжкових викликів у класах-наслідниках.
         * @return Поточний екземпляр Builder.
         */
        protected abstract T self();
    }

    protected Product(Builder<?> builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.calories = builder.calories;
        this.expirationDate = builder.expirationDate;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
    public int getCalories() {
        return calories;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Абстрактний метод для отримання повного опису продукту.
     * @return Рядок з описом.
     */
    public abstract String getDescription();
}
