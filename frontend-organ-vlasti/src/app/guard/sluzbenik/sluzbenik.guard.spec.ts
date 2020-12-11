import { TestBed } from '@angular/core/testing';

import { SluzbenikGuard } from './sluzbenik.guard';

describe('SluzbenikGuard', () => {
  let guard: SluzbenikGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(SluzbenikGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
