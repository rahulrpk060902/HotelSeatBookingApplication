import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelProfileComponent } from './hotel-profile.component';

describe('HotelProfileComponent', () => {
  let component: HotelProfileComponent;
  let fixture: ComponentFixture<HotelProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelProfileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HotelProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
