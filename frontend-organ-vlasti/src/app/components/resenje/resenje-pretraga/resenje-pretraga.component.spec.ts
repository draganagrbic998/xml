import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenjePretragaComponent } from './resenje-pretraga.component';

describe('ResenjePretragaComponent', () => {
  let component: ResenjePretragaComponent;
  let fixture: ComponentFixture<ResenjePretragaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResenjePretragaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResenjePretragaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
