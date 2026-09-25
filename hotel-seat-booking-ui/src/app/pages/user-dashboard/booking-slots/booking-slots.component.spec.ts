import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingSlotsComponent } from './booking-slots.component';

describe('BookingSlotsComponent', () => {
  let component: BookingSlotsComponent;
  let fixture: ComponentFixture<BookingSlotsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookingSlotsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookingSlotsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
