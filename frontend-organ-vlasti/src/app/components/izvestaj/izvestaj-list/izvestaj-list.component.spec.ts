import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IzvestajListComponent } from './izvestaj-list.component';

describe('IzvestajListComponent', () => {
  let component: IzvestajListComponent;
  let fixture: ComponentFixture<IzvestajListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IzvestajListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IzvestajListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
