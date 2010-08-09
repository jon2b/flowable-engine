/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.examples.bpmn.receivetask;

import org.activiti.engine.ActivityInstance;
import org.activiti.engine.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineTestCase;


/**
 * @author Joram Barrez
 */
public class ReceiveTaskTest extends ProcessEngineTestCase {

  @Deployment
  public void testWaitStateBehavior() {
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("receiveTask");
    ActivityInstance activityInstance = runtimeService.findExecutionByProcessInstanceIdAndActivityId(pi.getId(), "waitState");
    assertNotNull(activityInstance);
    
    runtimeService.signal(activityInstance.getId());
    assertProcessEnded(pi.getId());
  }

}
