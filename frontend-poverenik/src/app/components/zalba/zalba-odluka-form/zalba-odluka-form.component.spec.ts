import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaOdlukaFormComponent } from './zalba-odluka-form.component';

describe('ZalbaOdlukaFormComponent', () => {
  let component: ZalbaOdlukaFormComponent;
  let fixture: ComponentFixture<ZalbaOdlukaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaOdlukaFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaOdlukaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
