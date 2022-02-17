alter table forma_pagamento add column data_atualizacao timestamp null;
update forma_pagamento set data_atualizacao = timezone('utc', now());
alter table forma_pagamento alter column data_atualizacao set not null;