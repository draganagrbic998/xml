import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OdgovorPretragaComponent } from './odgovor-pretraga.component';

describe('OdgovorPretragaComponent', () => {
  let component: OdgovorPretragaComponent;
  let fixture: ComponentFixture<OdgovorPretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OdgovorPretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OdgovorPretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
