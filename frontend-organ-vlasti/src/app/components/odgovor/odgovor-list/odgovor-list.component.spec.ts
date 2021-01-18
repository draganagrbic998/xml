import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdgovorListComponent } from './odgovor-list.component';

describe('OdgovorListComponent', () => {
  let component: OdgovorListComponent;
  let fixture: ComponentFixture<OdgovorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdgovorListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdgovorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
