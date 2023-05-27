import { Subscription } from 'rxjs';
import { Component, OnDestroy } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication-service/authentication.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

/**
 * Renders login component.
 * Users can login with their own credentials.
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {
  hide: boolean = true;

  private subscription: Subscription;

  constructor(
    private loginService: AuthenticationService,
    private formBuilder: FormBuilder
  ) {}

  /**
   * Login form for user login.
   */
  loginForm: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  /**
   * Get login credentials from login form and sends them to the API.
   */
  onLogin() {
    this.subscription = this.loginService
      .login(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe({
        next: (data) => {},
        error: (err) => {
          this.loginForm.setErrors({
            unauthenticated: true,
          });
        },
      });
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
