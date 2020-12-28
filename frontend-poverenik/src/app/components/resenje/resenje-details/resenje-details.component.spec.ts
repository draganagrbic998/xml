import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenjeDetailsComponent } from './resenje-details.component';

describe('ResenjeDetailsComponent', () => {
  let component: ResenjeDetailsComponent;
  let fixture: ComponentFixture<ResenjeDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResenjeDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResenjeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
