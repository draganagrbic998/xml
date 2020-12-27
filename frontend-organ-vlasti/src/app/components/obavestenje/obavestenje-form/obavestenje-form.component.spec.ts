import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObavestenjeFormComponent } from './obavestenje-form.component';

describe('ObavestenjeFormComponent', () => {
  let component: ObavestenjeFormComponent;
  let fixture: ComponentFixture<ObavestenjeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ObavestenjeFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ObavestenjeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
