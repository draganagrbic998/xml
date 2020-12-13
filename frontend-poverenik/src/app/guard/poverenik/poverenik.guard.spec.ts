import { TestBed } from '@angular/core/testing';

import { PoverenikGuard } from './poverenik.guard';

describe('PoverenikGuard', () => {
  let guard: PoverenikGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(PoverenikGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
