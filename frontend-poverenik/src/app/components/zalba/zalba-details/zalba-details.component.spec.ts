import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaDetailsComponent } from './zalba-details.component';

describe('ZalbaDetailsComponent', () => {
  let component: ZalbaDetailsComponent;
  let fixture: ComponentFixture<ZalbaDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
