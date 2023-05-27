import { Subscription } from 'rxjs';

export function unsubscribeFrom(subscription: Subscription): void {
  if (subscription) {
    subscription.unsubscribe();
  }
}
