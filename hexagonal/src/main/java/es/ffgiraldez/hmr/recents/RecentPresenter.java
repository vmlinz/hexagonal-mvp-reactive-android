package es.ffgiraldez.hmr.recents;

import es.ffgiraldez.hmr.details.DetailsNavigation;
import es.ffgiraldez.hmr.navigation.Navigator;
import es.ffgiraldez.hmr.schedulers.SchedulerProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import rx.subscriptions.CompositeSubscription;
/**
 * @author Fernando Franco Giráldez
 */
@RequiredArgsConstructor
public class RecentPresenter {
    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final RecentInteractor interactor;
    private final Navigator navigator;
    private final SchedulerProvider schedulers;
    private RecentUserInterface ui;

    // ----------------------------------
    // PUBLIC API
    // ----------------------------------
    public void initialize(@NonNull RecentUserInterface userInterface) {
        this.ui = userInterface;
        subscriptions.add(ui.onBookClick().subscribe(
                identifier -> navigator.to(new DetailsNavigation(identifier))));
    }

    public void load() {
        subscriptions.add(
                interactor.recent()
                        .observeOn(schedulers.ui())
                        .subscribe(ui::show)
        );
    }

    public void reset() {
        subscriptions.unsubscribe();
    }
}
