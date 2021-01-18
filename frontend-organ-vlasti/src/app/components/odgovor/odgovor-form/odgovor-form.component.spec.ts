import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdgovorFormComponent } from './odgovor-form.component';

describe('OdgovorFormComponent', () => {
  let component: OdgovorFormComponent;
  let fixture: ComponentFixture<OdgovorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdgovorFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdgovorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
