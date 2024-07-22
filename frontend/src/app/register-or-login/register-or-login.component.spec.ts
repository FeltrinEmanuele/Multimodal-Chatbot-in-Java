import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterOrLoginComponent } from './register-or-login.component';

describe('RegisterOrLoginComponent', () => {
  let component: RegisterOrLoginComponent;
  let fixture: ComponentFixture<RegisterOrLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterOrLoginComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterOrLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
