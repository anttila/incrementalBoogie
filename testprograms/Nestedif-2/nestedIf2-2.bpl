var a : int;
var b : int;
var c : int;
var d : int;
var e : int;
var f : int;
var g : int;
var h : int;
var i : int;
var j : int;


procedure tooManyIfCases() returns()
ensures b == 5 ==> c == 2;
{
	havoc a, b, c, d, e, f, g, h;

	if (a < b){
		if (c == f){
			if (g >= h || b < f){
				if (d < c){
					assert 1==1;
				} else{
					assert 1==1;
				}
			} else{
				if (a < 10){
					assert 1==1;
				} else{
					if (i != j && ((b == 10) ==> (c != 5))){
						if (g >= h || b < f){
							if (10 < c){
								assert 1==1;
							} else{
								assert 1==1;
							}
						} else{
							if (a < 10){
								assert 1==1;
							} else{
								if (i != j && ((b == 10) ==> (c != 5))){
									assert 1==1;
								} else{
									assert 1==1;
								}
							}
						}
					} else{
						assert 1==1;
					}
				}
			}
		} else{
			if (c*2 == d || c == h){
				if (j >= a || e < 6 || (i == 5 && d < 0)){
					assert 1==1;
				} else{
					if (c == f){
						if (a+b <= h && b > g){
							if (d < c-a){
								if (h+b == e){
									assert 1==1;
								} else{
									if (c >= j){
										assert 1==1;
									} else{
										assert 1==1;
									}
								}
							} else{
								if (b != e){
									assert 1==1;
								} else{
									if (c+b > a){
										assert 1==1;
									} else{
										assert 1==1;
									}
								}
							}
						} else{
							if (b > 20){
								assert 1==1;
							} else{
								if (i != j && ((b == 10) ==> (c != 5))){
									if (c == d || c == h){
										if (j >= g || e < 6 || (i == 5 && d < 0)){
											assert 1==1;
										} else{
											if (c == f){
												if (a <= h && b > g){
													if (d < c){
														assert 1==1;
													} else{
														if (f <= g){
															if (g == 8 && g > b){
																assert 1==1;
															} else{
																assert 1==1;
															}
														} else{
															if (f <= i){
																assert 1==1;
															} else{
																if (h == e){
																	if (f <= g){
																		if (g == 15 && g > h){
																			assert 1==1;
																		} else{
																			assert 1==1;
																		}
																	} else{
																		if (a == c){
																			assert 1==1;
																		} else{
																			if (6 >= h){
																				if (f > g){
																					if (g == 8 && g > b){
																						assert 1==1;
																					} else{
																						assert 1==1;
																					}
																				} else{
																					if (f < i){
																						assert 1==1;
																					} else{
																						if (h == e){
																							assert 1==1;
																						} else{
																							if (c > a){
																								assert 1==1;
																							} else{
																								if (f <= g){
																									if (g == 8 || g > b){
																										assert 1==1;
																									} else{
																										assert 1==1;
																									}
																								} else{
																									if (f + i < 32){
																										assert 1==1;
																									} else{
																										if (h != e){
																											assert 1==1;
																										} else{
																											if (c > a){
																												assert 1==1;
																											} else{
																												assert 1==1;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			} else{
																				if (j < i){
																					assert 1==1;
																				} else{
																					assert 1==1;
																				}
																			}
																		}
																	}
																} else{
																	if (c > a){
																		assert 1==1;
																	} else{
																		assert 1==1;
																	}
																}
															}
														}
													}
												} else{
													if (b >= 20){
														assert 1==1;
													} else{
														if (i != j && ((b == 10) ==> (c != 5))){
															assert 1==1;
														} else{
															assert 1==1;
														}
													}
												}
											} else{
												if (b >= a ==> c == h){
													if (j >= g || e < 8 || (i == 4 && d < 0)){
														assert 1==1;
													} else{
														assert 1==1;
													}
												}
											}
										}
									}
								} else{
									assert 1==1;
								}
							}
						}
					} else{
						if (c == d || c == h){
							if (j >= g || h < 9 || (i == 10 && d < 3)){
								assert 1==1;
							} else{
								assert 1==1;
							}
						}
					}
				}
			}
		}
	} else{
		if (a > f){
			if (f < g){
				if (g == 5 && g > a){
					assert 1==1;
				} else{
					assert 1==1;
				}
			} else{
				if (b < f){
					assert 1==1;
				} else{
					if (i == f){
						assert 1==1;
					} else{
						if (g > d){
							assert 1==1;
						} else{
							if (a > f){
								if (f < g){
									if (g == 5 && g > a){
										assert 1==1;
									} else{
										assert 1==1;
									}
								} else{
									if (i < f){
										assert 1==1;
									} else{
										if (i == f){
											assert 1==1;
										} else{
											if (g > d){
												assert 1==1;
											} else{
												if (f <= g){
													if (g == 15 && g > h){
														assert 1==1;
													} else{
														assert 1==1;
													}
												} else{
													if (a != j){
														assert 1==1;
													} else{
														if (6 >= h){
															if (f <= g){
																if (g == 8 && g > b){
																	assert 1==1;
																} else{
																	assert 1==1;
																}
															} else{
																if (f < i){
																	assert 1==1;
																} else{
																	if (h == e){
																		assert 1==1;
																	} else{
																		if (c > a){
																			assert 1==1;
																		} else{
																			if (f <= g){
																				if (g == 8 && g > b){
																					assert 1==1;
																				} else{
																					assert 1==1;
																				}
																			} else{
																				if (f < i){
																					assert 1==1;
																				} else{
																					if (h == e){
																						assert 1==1;
																					} else{
																						if (c < a){
																							assert 1==1;
																						} else{
																							assert 1==1;
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														} else{
															if (b < i){
																assert 1==1;
															} else{
																assert 1==1;
															}
														}
													}
												}
											}
										}
									}
								}
							} else{
								if (f <= g){
									if (g == 18 && g > h){
										assert 1==1;
									} else{
										assert 1==1;
									}
								} else{
									if (a != c){
										assert 1==1;
									} else{
										if (6 >= h){
											if (f <= g){
												if (g == 8 && g > b){
													assert 1==1;
												} else{
													assert 1==1;
												}
											} else{
												if (f < i){
													assert 1==1;
												} else{
													if (h == e){
														assert 1==1;
													} else{
														if (c > a){
															assert 1==1;
														} else{
															if (f <= g){
																if (g == 15 && g > b){
																	assert 1==1;
																} else{
																	assert 1==1;
																}
															} else{
																if (f < i){
																	assert 1==1;
																} else{
																	if (h != e){
																		assert 1==1;
																	} else{
																		if (c <= b){
																			assert 1==1;
																		} else{
																			assert 1==1;
																		}
																	}
																}
															}
														}
													}
												}
											}
										} else{
											if (j <= i){
												assert 1==1;
											} else{
												assert 1==1;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else{
			if (f <= g){
				if (g == 8 && g > b){
					assert 1==1;
				} else{
					assert 1==1;
				}
			} else{
				if (f < i){
					assert 1==1;
				} else{
					if (h == e){
						assert 1==1;
					} else{
						if (c > a){
							assert 1==1;
						} else{
							if (f <= g){
								if (g == 8 && g > b){
									assert 1==1;
								} else{
									assert 1==1;
								}
							} else{
								if (f < i){
									assert 1==1;
								} else{
									if (h == e){
										assert 1==1;
									} else{
										if (c > a){
											assert 1==1;
										} else{
											assert 1==1;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	assume b == 5;
	if (b==5){
		c := 2;
	} else{
		c := 3;
	}

}