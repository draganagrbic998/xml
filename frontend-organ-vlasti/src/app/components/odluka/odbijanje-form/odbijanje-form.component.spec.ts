import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdbijanjeFormComponent } from './odbijanje-form.component';

describe('OdbijanjeFormComponent', () => {
  let component: OdbijanjeFormComponent;
  let fixture: ComponentFixture<OdbijanjeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdbijanjeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdbijanjeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
