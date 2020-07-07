package ir.vasl.navigationcomponentimpl.view.callbacks;

public interface GlobalListCallback<T> {

    default void onItemClicked() {
    }

    default void onItemClicked(T t) {
    }

    default void onTrainingCategoryClicked(T t, int pos) {
    }

    default void onTrainingSubCategoryClicked(T t) {
    }


    default void onMoreClicked() {
    }

    default void onMoreClicked(T t) {
    }

    default void onFavClicked() {
    }

    default void onExerciseClicked(T t) {
    }

}
