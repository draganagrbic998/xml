import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZalbaFormComponent } from './zalba-form.component';

describe('ZalbaFormComponent', () => {
  let component: ZalbaFormComponent;
  let fixture: ComponentFixture<ZalbaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZalbaFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ZalbaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
