import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookedSeatsComponent } from './booked-seats.component';

describe('BookedSeatsComponent', () => {
  let component: BookedSeatsComponent;
  let fixture: ComponentFixture<BookedSeatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookedSeatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookedSeatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
