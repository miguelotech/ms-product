package com.synopsis.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MsProductApplicationTests {

	@Test
	void applicationClassIsLoadable() {
		assertThat(new MsProductApplication()).isNotNull();
	}
}
