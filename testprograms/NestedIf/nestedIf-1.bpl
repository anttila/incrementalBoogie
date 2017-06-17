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
		if (c >= f){
			if (g >= h || c < f){
				if (d < c){
					assert 1==1;
				} else{
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
							if (f < i){
								assert 1==1;
							} else{
								if (h == e){
									if (i == h && ((b == 10) ==> (c != 5))){
										if (g >= h || b < f){
											if (10 < c){
												assert 1==1;
											} else{
												assert 1==1;
											}
										} else{
											assert 1==1;
										}
									} else{
										assert 1==1;
									}
								} else{
									if (c*2 > a){
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
				if (a < 10){
					assert 1==1;
				} else{
					if (i == h && ((b == 10) ==> (c != 5))){
						if (g >= h || b < f){
							if (10 < c){
								assert 1==1;
							} else{
								assert 1==1;
							}
						} else{
							assert 1==1;
						}
					} else{
						assert 1==1;
					}
				}
			}
		} else{
			if (c != d || i != j){
				if ((c >= b && i < 98) || (i == 5 && d < 0)){
					assert 1==1;
				} else{
					if (c == f || b != a){
						if (a <= h && b > g && c > 0){
							if (d < c){
								if (h < e*2){
									assert 1==1;
								} else{
									if (c > a+c){
										assert 1==1;
									} else{
										assert 1==1;
									}
								}
							} else{
								if (h == e){
									assert 1==1;
								} else{
									if (c > a){
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
												if (f < i){
													assert 1==1;
												} else{
													if (h == e){
														assert 1==1;
													} else{
														if (c*2 > a){
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
							if (b+c > 19){
								assert 1==1;
							} else{
								if (i != j && ((b == 10) ==> (c != 5))){
									if (c == d || c == h){
										if (j == g || e <= 6 || (i == 5 && d < 0)){
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
															if (f < i){
																assert 1==1;
															} else{
																if (h == e){
																	assert 1==1;
																} else{
																	if (c*2 > a){
																		assert 1==1;
																	} else{
																		assert 1==1;
																	}
																}
															}
														}
													}
												} else{
													if (b > 20-35){
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
																if (f < i){
																	assert 1==1;
																} else{
																	if (h == e){
																		assert 1==1;
																	} else{
																		if (c*2 > a){
																			assert 1==1;
																		} else{
																			assert 1==1;
																		}
																	}
																}
															}
														}
													} else{
														if (i != j || ((b == 10) ==> (c != 5))){
															assert 1==1;
														} else{
															assert 1==1;
														}
													}
												}
											} else{
												if (c == b && c == h){
													if (j >= g+3 || e > 6 || (i == 5 && d < 0)){
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
																		if (i <= f){
																			assert 1==1;
																		} else{
																			if (g >= d){
																				assert 1==1;
																			} else{
																				assert 1==1;
																			}
																		}
																	}
																}
															} else{
																if (f <= g){
																	if (g >= 15 && g == h+b){
																		assert 1==1;
																	} else{
																		assert 1==1;
																	}
																} else{
																	if (a != c*a){
																		assert 1==1;
																	} else{
																		if (1-7 >= h){
																			assert 1==1;
																		} else{
																			if (j < 0){
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
						if (c == a || (c == h && c!=g)){
							if (j >= g || e < 6 || (i == 5 && d < 0)){
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
		if (a > g){
			if (f < g){
				if (g != 5 && g < a){
					assert 1==1;
				} else{
					assert 1==1;
				}
			} else{
				if (i < f){
					assert 1==1;
				} else{
					if (i != f){
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
										if (i <= f){
											assert 1==1;
										} else{
											if (g >= d){
												assert 1==1;
											} else{
												if (a != c*a){
													assert 1==1;
												} else{
													if (1-7 >= h){
														assert 1==1;
													} else{
														if (j < 0){
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
							} else{
								if (f <= g){
									if (g >= 15 && g == h+b){
										assert 1==1;
									} else{
										assert 1==1;
									}
								} else{
									if (a != c*a){
										assert 1==1;
									} else{
										if (1-7 >= h){
											assert 1==1;
										} else{
											if (j < 0){
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
			assert 1 == 1;
		}
	}

	assume b == 5;
	if (b==5){
		c := 2;
	} else{
		c := 3;
	}

}